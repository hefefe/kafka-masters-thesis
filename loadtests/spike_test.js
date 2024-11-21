import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  stages: [
    { duration: '2m', target: parseInt(__ENV.TARGET) },
    { duration: '1m', target: 0 }
  ],
};

export default () => {
  const urlRes = http.get(`http://localhost:1234/api/v1/message/test?size=${__ENV.SIZE}&unit=${__ENV.UNIT}`);
  sleep(1);
};