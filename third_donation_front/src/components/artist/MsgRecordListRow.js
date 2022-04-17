import { navigate } from '@reach/router';
import axios_apis from '../../core/axios';

const MsgRecordListRow = ({ messages }) => {
  const navigateTo = (link) => {
    navigate(link);
  };

  return (
    <tbody>
      {messages.map((msg) => (
        <tr key={msg.message} className="msgRow">
          {/* <td className="rowId">{msg.buyer.id}</td> */}
          <td className="rowId author_list_pp">
            <span onClick={() => navigateTo(`/profile/${msg.buyer.id}`)}>
              <img
                className="lazy"
                src={
                  msg.buyer.imagePath
                    ? `${axios_apis.file}/${msg.buyer.imagePath}`
                    : '/img/기본프로필이미지.png'
                }
                alt=""
              />
            </span>
          </td>
          <td className="rowUserName">{msg.buyer.username}</td>
          <td className="rowMsg">{msg.message}</td>
          <td className="rowTokenName">{msg.tokenName}</td>
          <td className="rowDate">{msg.dateTraded.substr(0, 10)}</td>
        </tr>
      ))}
    </tbody>
  );
};
export default MsgRecordListRow;
