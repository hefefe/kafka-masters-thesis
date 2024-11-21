import http from 'k6/http';
import {sleep} from 'k6';

export const options = {
  executor: 'ramping-arrival-rate',
  stages: [
    { duration: '2h', target: parseInt(__ENV.TARGET) },
  ],
};

export default () => {
  const urlRes = http.get(`http://localhost:1234/api/v1/message/test?size=${__ENV.SIZE}&unit=${__ENV.UNIT}`);
  sleep(1);
};