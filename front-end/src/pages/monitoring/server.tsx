import React from 'react';
import { NextPage } from 'next';
import style from '@/styles/MainPage.module.scss';
import TopLayout from '@/components/templates/TopLayout';
import ServerGraph from '@/components/atoms/ServerGraph';

const ServerMonitoring: NextPage = () => {
  const src = {
    HeapUsed: 'panelId=58',
    NonHeapUsed: 'panelId=60',
    InfoLogs: 'panelId=6',
    ErrorLogs: 'panelId=10',
    WarnLogs: 'panelId=14',
    DebugLogs: 'panelId=16',
    TraceLogs: 'panelId=20',
    CpuUsage: 'panelId=95',
    LoadAverage: 'panelId=96',
    ProcessOpenFiles: 'panelId=66',
    ResponseCount: 'panelId=4',
    ResponseTime: 'panelId=2',
    ErrorLogsDetail: {
      src: 'https://k9c201.p.ssafy.io/grafana/d-solo/b4d29df7-664b-4ea1-82ba-022f535c0356/error-log?orgId=1',
      panelId: 'panelId=1',
    },
    WarnLogCount: {
      src: 'https://k9c201.p.ssafy.io/grafana/d-solo/b4d29df7-664b-4ea1-82ba-022f535c0356/error-log?orgId=1',
      panelId: 'panelId=3',
    },
    ErrorLogCount: {
      src: 'https://k9c201.p.ssafy.io/grafana/d-solo/b4d29df7-664b-4ea1-82ba-022f535c0356/error-log?orgId=1',
      panelId: 'panelId=4',
    },
  };

  const randomString = Math.random().toString(36).substring(7);
  const uniqueErrorLogsDetailURL = `${src.ErrorLogsDetail.src}&random=${randomString}`;
  const uniqueWarnLogCount = `${src.WarnLogCount.src}&random=${randomString}`;
  const uniqueErrorLogCount = `${src.ErrorLogCount.src}&random=${randomString}`;

  return (
    <TopLayout>
      <div className={`${style.serverPageContainer}`}>
        <div className="flex justify-center items-center">
          <div className="flex w-full">
            <div className="flex flex-col" style={{ width: '45%' }}>
              <div className="flex w-full">
                <ServerGraph src={src.HeapUsed} from="2s" />
                <ServerGraph src={src.NonHeapUsed} from="2s" />
              </div>
              <div className="flex">
                {/* <ServerGraph src={src.ErrorLogsDetail} /> */}
                <ServerGraph src={uniqueErrorLogsDetailURL} panelId={src.ErrorLogsDetail.panelId} from="now-1h" />
                <ServerGraph src={src.InfoLogs} />
              </div>
              <div className="flex">
                <ServerGraph src={src.DebugLogs} />
                <ServerGraph src={src.TraceLogs} />
              </div>
              <div className="flex">
                <ServerGraph src={uniqueWarnLogCount} panelId={src.ErrorLogCount.panelId} from="now-1h" />
                <ServerGraph src={uniqueErrorLogCount} panelId={src.WarnLogCount.panelId} from="now-1h" />
              </div>
            </div>
            <div className="flex flex-col" style={{ width: '55%' }}>
              <div className="w-full">
                <ServerGraph src={src.CpuUsage} />
              </div>
              <ServerGraph src={src.LoadAverage} />
              <ServerGraph src={src.ProcessOpenFiles} />
              <div className="flex">
                <ServerGraph src={src.ResponseCount} />
                <ServerGraph src={src.ResponseTime} />
              </div>
            </div>
          </div>
        </div>
      </div>
    </TopLayout>
  );
};

export default ServerMonitoring;
