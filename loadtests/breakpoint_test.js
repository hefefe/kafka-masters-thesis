import http from 'k6/http';
import {sleep} from 'k6';
import { vu } from 'k6/execution';

export const options = {
  executor: 'ramping-arrival-rate',
  stages: [
    { duration: '2h', target: parseInt(__ENV.TARGET) },
  ],
};

export default () => {
  const port = parseInt(__ENV.BASEPORT) + (vu.idInTest - 1) % parseInt(__ENV.INSTANCES);
  const urlRes = http.get(`http://localhost:`+port+`/api/v1/message/test?size=${__ENV.SIZE}&unit=${__ENV.UNIT}`);
  sleep(1);
};