import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  stages: [
    { duration: '2m', target: 2000 },
    { duration: '1m', target: 0 }
  ],
};

export default () => {
  const urlRes = http.get('http://localhost:1234/api/v1/message/test?size=10&unit=KB');
  sleep(1);
};