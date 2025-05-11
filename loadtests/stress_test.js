import http from 'k6/http';
import { sleep } from 'k6';
import { vu } from 'k6/execution';

export const options = {
  stages: [
    { duration: '10m', target: parseInt(__ENV.TARGET) }, 
    { duration: '30m', target: parseInt(__ENV.TARGET) },
    { duration: '5m', target: 0 }, 
  ],
};

export default () => {
  const port = parseInt(__ENV.BASEPORT) + (vu.idInTest - 1) % parseInt(__ENV.INSTANCES);
  const urlRes = http.get(`http://localhost:${port}/api/v1/message/test?size=${__ENV.SIZE}&unit=${__ENV.UNIT}`);
  sleep(1);
}