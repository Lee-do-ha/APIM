import { TUserDataList, Pageable } from '@/types/User';
import axiosInstance from './axiosInstance';

export async function getUserInfo() {
  try {
    const response = await axiosInstance({
      method: 'GET',
      url: '/member/auth/mypage',
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function getMembers({ page, size }: Pageable) {
  try {
    const response = await axiosInstance({
      method: 'GET',
      url: '/member/auth/all',
      params: {
        page,
        size,
      },
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function createMembers(props: TUserDataList) {
  try {
    const response = await axiosInstance({
      method: 'POST',
      url: '/member/auth/sign-up',
      data: props,
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function getTeamInfo(props: string) {
  try {
    const response = await axiosInstance({
      method: 'GET',
      url: '/member/team',
      params: { teamName: props },
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error(error);
    return null;
  }
}
