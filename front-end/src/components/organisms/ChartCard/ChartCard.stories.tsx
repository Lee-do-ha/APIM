import ChartCard from '.';

export default {
  title: 'organisms/ChartCard',
  tags: ['autodocs'],
  component: ChartCard,
  argTypes: {
    title: {
      description: 'API 이름',
    },
    children: {
      description: '해당 차트',
    },
    onClick: {
      description: '클릭시 차트 상세 페이지로 이동',
    },
  },
};

export const Chart = {
  args: {
    title: 'CPU Usage',
    children: <div>차트</div>,
    explain: <p>CPU Usage에 관한 차트입니다</p>,
    onclick: () => {},
  },
};
