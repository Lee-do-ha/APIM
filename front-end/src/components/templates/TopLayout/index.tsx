import React from 'react';
import NavBar from '@/components/organisms/NavBar';
import SpeedDialF from '@/components/atoms/SpeedDial';
import style from './TopLayout.module.scss';
/**
 * TopLayout 컴포넌트
 * @param {object} userInfo - 사원 정보
 */

function TopLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className={`${style.page}`}>
      {/* Top NavBar */}
      <NavBar position="top" />
      <div>{children}</div>
      <SpeedDialF />
    </div>
  );
}

export default TopLayout;
