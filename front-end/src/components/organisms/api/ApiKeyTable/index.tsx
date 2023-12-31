import { ApiKeyTableProps, InputTableProps, OutputTableProps } from '@/types/props/ApiProps';
import styles from './ApiKeyTable.module.scss';

function ApiKeyTable({ type, ...props }: ApiKeyTableProps) {
  const inputHeaders = ['이름', '타입', '설명', '필수'];
  const outputHeaders = ['이름', '타입', '설명'];

  if (type === 'input') {
    const { keyList } = props as InputTableProps;
    return (
      <div>
        <table className="w-full">
          <thead>
            <tr>
              {inputHeaders.map((header) => (
                <th
                  key={header}
                  className=" pr-3 py-2"
                  style={{
                    backgroundColor: '#f6f6f6',
                    borderTop: '1px solid #dddddd',
                    borderBottom: '1px solid #dddddd',
                  }}
                >
                  {header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className="bg-white w-full">
            {keyList.map((list) => (
              <tr key={list.name} className="text-center">
                <td className={styles.tr}>
                  <div>{list.name}</div>
                </td>
                <td className={styles.tr}>
                  <div>{list.type}</div>
                </td>
                <td className={styles.tr}>
                  <div>{list.description}</div>
                  <div className="italic itdaSecondary text-sm">Example : {list.example}</div>
                </td>
                <td className={`pr-3 ${styles.tr}`}>
                  <div>{list.required === 'true' ? 'O' : 'X'}</div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
  if (type === 'output') {
    const { keyList } = props as OutputTableProps;
    return (
      <div>
        <table className="w-full">
          <thead>
            <tr>
              {outputHeaders.map((header) => (
                <th
                  key={header}
                  className="py-2"
                  style={{
                    backgroundColor: '#f6f6f6',
                    borderTop: '1px solid #dddddd',
                    borderBottom: '1px solid #dddddd',
                  }}
                >
                  {header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className="bg-white w-full">
            {keyList.map((list) => (
              <tr key={list.name} className="text-center">
                <td className={styles.trOutput}>
                  <div>{list.name}</div>
                </td>
                <td className={styles.trOutput}>
                  <div>{list.type}</div>
                </td>
                <td className={styles.trOutput}>
                  <div>{list.description}</div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default ApiKeyTable;
