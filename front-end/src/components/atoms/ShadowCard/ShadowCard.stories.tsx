import ShadowCard from '.';

export default {
  title: 'atoms/ShadowCard',
  tags: ['autodocs'],
  component: ShadowCard,
  argTypes: {
    children: {
      description: '카드 내부에 들어갈 내용',
    },
    type: {
      description: '[small] border-radius: 1.5rem; [big] border-radius: 0.25rem;',
    },
  },
};

export const SmallCard = {
  args: {
    children: <span>Small Card</span>,
    type: 'small',
  },
};

export const BigCard = {
  args: {
    children: <span>Big Card</span>,
    type: 'big',
  },
};
