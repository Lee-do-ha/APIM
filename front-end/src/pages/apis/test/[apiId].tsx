import { useEffect } from 'react';
import { NextPage, GetStaticProps, GetStaticPaths } from 'next';
import Link from 'next/link';
import BothLayout from '@/components/templates/BothLayout';
import ApiSideBar from '@/components/organisms/ApiSideBar';
import useUserStore from '@/store/useUserStore';
import { useQuery, QueryClient } from 'react-query';
import { useRouter } from 'next/router';
import { TCategoryList, IApiTestInfo } from '@/types/Api';
import { getCategoryList, getApiTestInfo } from '@/utils/axios/api';
import { dehydrate } from 'react-query/hydration';
import PageLoading from '@/components/atoms/PageLoading';
import GoBack from '@/components/atoms/GoBack';
import useTestStore from '@/store/useTestStore';
import useMyApi from '@/hooks/useMyApi';
import { Spinner } from '@nextui-org/react';
import Editor from '@monaco-editor/react';
import { isValidJSON, formatJsonToCurl, JsonData, safeJsonParse } from '@/utils/json';
import ApiTestForm from '@/components/organisms/api/ApiTestForm';
import ApiTestLayout from '@/components/templates/ApiTestLayout';
import Copy from '@/components/atoms/Copy';

type SSGProps = {
  apiId: number;
};

const ApiDetail: NextPage<SSGProps> = ({ apiId }: SSGProps) => {
  const router = useRouter();
  const { selectedTeam } = useUserStore();
  const {
    testRequest,
    testResponse,
    status,
    loading,
    resetParams,
    resetStatus,
    resetTestRequest,
    resetTestResponse,
    setTestResponse,
  } = useTestStore();
  const { data: categoryList } = useQuery<TCategoryList>('categoryList', getCategoryList);
  const { useCategoryList, provideCategoryList } = useMyApi(selectedTeam);

  const { data: apiTestInfo } = useQuery<IApiTestInfo>(`apiTestInfo ${apiId}`, async () => {
    const result = await getApiTestInfo(apiId);
    return result;
  });

  useEffect(() => {
    setTestResponse(apiTestInfo?.outputExample || '');
    // 언마운트 될 때 스토어 값 초기화
    return () => {
      resetStatus();
      resetTestRequest();
      resetTestResponse();
    };
  }, [apiTestInfo, resetParams, resetStatus, resetTestRequest, resetTestResponse, setTestResponse]);

  if (!categoryList || !useCategoryList || !provideCategoryList || !apiTestInfo) {
    return <PageLoading />;
  }

  const editorOptions = {
    readOnly: true,
    minimap: {
      enabled: false,
    },
    scrollBeyondLastLine: false,
  };

  return (
    <BothLayout>
      <ApiSideBar
        useCategoryList={useCategoryList}
        provideCategoryList={provideCategoryList}
        openCategory={apiTestInfo.categoryId}
        categoryList={categoryList}
        defaultSelectedKey={(router.query.defaultSelectedKey as string) || 'all'}
        openMyCategory={apiTestInfo.categoryId}
      />
      <div>
        <GoBack label={`${apiTestInfo?.title} API 테스트`} />
        <ApiTestLayout>
          {/* Headers */}
          <div>
            <div className="itdaText font-semibold text-base">Headers</div>
            <table className="w-full my-2">
              <tbody className="bg-white w-full border">
                <tr className="text-center">
                  <td className="py-3">Authorization</td>
                  <td className="py-3">Authorization: &#36;&#123;TEAM_KEY&#125;</td>
                </tr>
              </tbody>
            </table>
            <Link href={{ pathname: '/team/token', query: { category: apiTestInfo.categoryName } }}>
              <div className="flex justify-end text-sm underline itdaSecondary cursor-pointer">키 확인하기</div>
            </Link>
          </div>

          {/* 쿼리 입력 표 */}
          <ApiTestForm apiTestInfo={apiTestInfo} />

          {/* 요청 예시 */}
          <div>
            <div className="flex justify-between items-center mb-2">
              <div className="itdaText font-semibold text-base">Curl</div>
              <Copy
                copyText={
                  isValidJSON(testRequest as string)
                    ? formatJsonToCurl(JSON.parse(testRequest as string) as JsonData)
                    : ''
                }
              />
            </div>
            <Editor
              height="100px"
              language="json"
              value={
                isValidJSON(testRequest as string)
                  ? formatJsonToCurl(JSON.parse(testRequest as string) as JsonData)
                  : ''
              }
              theme="vs-dark"
              options={editorOptions}
            />
          </div>

          {/* 상태 */}
          <div className="flex gap-5 items-center justify-between">
            <div className="flex gap-2 items-center">
              <div
                className={`w-5 h-5 rounded-full ${
                  status && status.toString().startsWith('2') ? 'bg-green-600' : 'bg-red-600'
                }`}
              />
              <div className="font-bold">{status}</div>
              <div>{loading && <Spinner size="sm" />}</div>
            </div>
            <Copy copyText={isValidJSON(testResponse) ? JSON.stringify(safeJsonParse(testResponse), null, 2) : ''} />
          </div>

          {/* 응답 결과 */}
          <div>
            <Editor
              height="500px"
              language="json"
              value={isValidJSON(testResponse) ? JSON.stringify(safeJsonParse(testResponse), null, 2) : ''}
              theme="vs-dark"
              options={editorOptions}
            />
          </div>
        </ApiTestLayout>
      </div>
    </BothLayout>
  );
};

export const getStaticPaths: GetStaticPaths = async () => ({
  paths: [],
  fallback: true,
});

export const getStaticProps: GetStaticProps = async ({ params }) => {
  const apiId = Number(params?.apiId);
  const queryClient = new QueryClient();
  await queryClient.prefetchQuery(`apiTestInfo ${apiId}`, () => getApiTestInfo(apiId));
  return {
    props: { dehydratedState: dehydrate(queryClient), apiId },
  };
};

export default ApiDetail;
