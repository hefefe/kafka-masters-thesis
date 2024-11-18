import http from 'k6/http';
import {sleep} from 'k6';

export const options = {
  executor: 'ramping-arrival-rate',
  stages: [
    { duration: '2h', target: 20000 },
  ],
};

export default () => {
  const urlRes = http.get('http://localhost:1234/api/v1/message/test?size=10&unit=KB');
  sleep(1);
};