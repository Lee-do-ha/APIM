import Notice from '.';

export default {
  title: 'organisms/Notice',
  tags: ['autodocs'],
  component: Notice,
  argTypes: {
    onClick: {
      description: '쪽지 클릭시 상세 페이지 이동 함수 지정',
    },
    onCheckboxToggle: {
      description: 'checkbox 체크시 추가/제외 함수 지정',
    },
    noticeInfo: {
      description: '쪽지 정보 데이터를 넣습니다.',
      control: {
        type: 'object',
      },
    },
    position: {
      description: 'page에서 사용할 건지, nav에서 사용할건지 선택',
    },
    type: {
      description: '받은 쪽지인지 보낸 쪽지인지 선택',
    },
  },
};

export const PageNotice = {
  args: {
    noticeInfo: {
      noticeId: 1,
      from: [
        {
          fromName: 'ITDA',
          fromImgUrl: '/images/profileImg.png',
        },
      ],
      title: "'이미지 검색하기' API 제공 신청이 승인되었습니다.",
      check: true,
      createdAt: new Date('2023-10-16 14:47:30'),
    },
    isSelected: true,
    onCheckboxToggle: () => {},
    onClick: () => {},
    position: 'page',
  },
};

export const NavNotice = {
  args: {
    noticeInfo: {
      noticeId: 1,
      from: [
        {
          fromName: 'ITDA',
          fromImgUrl: '/images/profileImg.png',
        },
      ],
      title: "'이미지 검색하기' API 제공 신청이 승인되었습니다.",
      check: true,
      createdAt: new Date('2023-10-16 14:47:30'),
    },
    onClick: () => {},
    position: 'nav',
  },
};
