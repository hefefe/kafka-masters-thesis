import http from 'k6/http';
import { vu } from 'k6/execution';

export const options = {
  discardResponseBodies: true,
  scenarios: {
    kafka: {
      executor: 'ramping-arrival-rate',
      preAllocatedVUs: 5000,
      maxVUs: 20000,
      stages: [
        { duration: '2h', target: parseInt(__ENV.TARGET) },
      ],
    },
  },
};

export default () => {
  const params = {
    headers: {
        'Connection': 'keep-alive',
    },
};
  const port = parseInt(__ENV.BASEPORT) + (vu.idInTest - 1) % parseInt(__ENV.INSTANCES);
  const urlRes = http.get(`http://localhost:${port}/api/v1/message/test?size=${__ENV.SIZE}&unit=${__ENV.UNIT}`, params);
};