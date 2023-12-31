// import { useState } from 'react';
// import { NextPage, GetServerSideProps } from 'next';
// import { QueryClient, useQuery } from 'react-query';
// import { dehydrate } from 'react-query/hydration';
// import { getMembers } from '@/utils/axios/user';
// import { IUserInfo } from '@/types/User';
// import TopLayout from '@/components/templates/TopLayout';
// import GoBack from '@/components/atoms/GoBack';
// import UserListBox from '@/components/organisms/UserListBox';
// import ShadowCard from '@/components/atoms/ShadowCard';
// import StyledPagination from '@/components/atoms/StyledPagination';
// import styles from '@/components/templates/TopLayout/TopLayout.module.scss';

// const MemberList: NextPage = () => {
//   const [clickPage, setClickPage] = useState(1);
//   const { data: memberList } = useQuery<IUserInfo>(`memberList ${clickPage}`, async () => {
//     const result = await getMembers({ page: clickPage - 1, size: 10 });
//     return result;
//   });

//   if (memberList === undefined) {
//     return null;
//   }

//   const handlePageClick = (clickedPage: number) => {
//     setClickPage(clickedPage);
//   };

//   return (
//     <TopLayout>
//       <div className={styles.topPageContainer}>
//         <div style={{ margin: '0 200px' }}>
//           <GoBack label="사원 관리" />
//           <div className="mt-8">
//             <ShadowCard type="big">
//               <UserListBox userList={memberList.content} />
//               <div className="flex justify-center mt-4">
//                 <StyledPagination
//                   totalPage={memberList?.totalPages}
//                   clickPage={clickPage}
//                   onClickPage={handlePageClick}
//                 />
//               </div>
//             </ShadowCard>
//           </div>
//         </div>
//       </div>
//     </TopLayout>
//   );
// };

// export const getServerSideProps: GetServerSideProps = async () => {
//   const queryClient = new QueryClient();
//   await queryClient.prefetchQuery('memberList', () => getMembers({ page: 0, size: 8 }));
//   return {
//     props: {
//       dehydratedState: dehydrate(queryClient),
//     },
//   };
// };

// export default MemberList;

import React from 'react';
import { IUser } from '@/types/User';
import BothLayout from '@/components/templates/BothLayout';
import MemberBox from '@/components/organisms/MemberBox';
import { NextPage } from 'next';
import { getUserInfo } from '@/utils/axios/user';
import { useQuery } from 'react-query';
import { getCategoryList } from '@/utils/axios/api';
import { TCategoryList } from '@/types/Api';
// import styles from '@/components/templates/TopLayout/TopLayout.module.scss';
import MemberPageSideBar from '@/components/organisms/MemberPageSideBar';

const MemberList: NextPage = () => {
  const { data: userInfo } = useQuery<IUser>('userInfo', getUserInfo);
  const { data: categoryList } = useQuery<TCategoryList>('categoryList', getCategoryList);

  if (userInfo === undefined || categoryList === undefined) {
    return null;
  }

  return (
    <BothLayout>
      <MemberPageSideBar />
      <MemberBox userInfo={userInfo} type="list" />
    </BothLayout>
  );
};

export default MemberList;
