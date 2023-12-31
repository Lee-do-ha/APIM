import { useState } from 'react';
import { ITeamInfo } from '@/types/User';
import { useQuery } from 'react-query';
import StyledPagination from '@/components/atoms/StyledPagination';
import { getTeamInfo } from '@/utils/axios/user';
import Modal from '@/components/organisms/Modal';
import DropDown from '@/components/atoms/DropDown';
import NoticeSendBox from '../NoticeSendBox';
import styles from './TeamTable.module.scss';

interface Props {
  team: string;
}

interface Member {
  name: string;
  employeeId: string;
}

function MemberTable({ team }: Props) {
  const [clickPage, setClickPage] = useState(1);
  const [selectedMember, setSelectedMember] = useState<Member | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { data: teamInfo } = useQuery<ITeamInfo>(['teamInfo', clickPage, team], async () => {
    const result = await getTeamInfo({ page: clickPage - 1, size: 7, teamName: team });
    return result;
  });
  const handlePageClick = (clickedPage: number) => {
    setClickPage(clickedPage);
  };

  const headers = ['사번', '성명', '부서', '직무', '이메일'];

  if (teamInfo === undefined) {
    return null;
  }

  const memberList = teamInfo.teamMembers.content;

  const onModalHandler = (member: Member) => {
    setSelectedMember(member);
    setIsModalOpen(true);
  };

  return (
    <div>
      <table className="w-full">
        <colgroup>
          <col style={{ width: '15%' }} />
          <col style={{ width: '15%' }} />
          <col style={{ width: '15%' }} />
          <col style={{ width: '20%' }} />
          <col style={{ width: '35%' }} />
        </colgroup>
        <thead className={`w-full ${styles.header}`}>
          <tr>
            {headers?.map((header) => (
              <th key={header} className={styles.th}>
                {header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody className="bg-white w-full">
          {memberList && memberList?.length > 0 ? (
            memberList.map((member) => (
              <tr key={member.employeeId} className="text-sm">
                <td className={`${styles.tr} text-center`}>{member.employeeId}</td>
                <td className={`${styles.tr} text-center`}>
                  <DropDown
                    type="modal"
                    trigger={<button type="button">{member.name}</button>}
                    list={[
                      {
                        title: '쪽지 보내기',
                        icon: 'message',
                        onModalHandler: () => onModalHandler(member),
                      },
                    ]}
                  />
                </td>
                <td className={`${styles.tr} text-center`}>{member.department}</td>
                <td className={`${styles.tr} text-center`}>{member.position}</td>
                <td className={`${styles.tr}`}>{member.email}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={4} className="text-center" style={{ height: '60px' }}>
                사원이 없습니다
              </td>
            </tr>
          )}
        </tbody>
      </table>
      <div className="flex justify-center mt-8">
        <StyledPagination
          totalPage={teamInfo?.teamMembers.totalPages}
          clickPage={clickPage}
          onClickPage={handlePageClick}
        />
      </div>

      {isModalOpen && selectedMember && (
        <Modal type="server" onClose={() => setIsModalOpen(false)}>
          <NoticeSendBox
            sendName={selectedMember.name}
            sendId={selectedMember.employeeId}
            onSendBoxClose={() => setIsModalOpen(false)}
          />
        </Modal>
      )}
    </div>
  );
}

export default MemberTable;
