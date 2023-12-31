import { IApiNameList } from '../Api';

export interface CategoryProps {
  categoryName: string;
  apiList: IApiNameList;
  categoryId: number;
  isOpen: boolean;
  my: boolean;
  type: 'apis' | 'use' | 'provide' | 'admin';
}
