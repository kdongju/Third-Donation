import axios from 'axios';

export const API_URL = 'http://j6e207.p.ssafy.io:32090/api';
export const API_TIME_SOURCE = 'https://worldtimeapi.org/api/timezone/Asia/Seoul';
export const Axios = axios.create({
  baseURL: API_URL,
});
export const Canceler = axios.CancelToken.source();

const nft_base = '/nfts';
const user_base = '/users';

const apis = {
  nfts: {
    items: `${nft_base}/items`,
    list: `${nft_base}/nft`,
    sales: `${nft_base}/sales`,
  },
  users: {
    user: `${user_base}`,
    img: `${user_base}/img`,
    profile: `${user_base}/profile`,
  },
};

export default apis;
