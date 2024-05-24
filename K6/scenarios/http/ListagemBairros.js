import http from 'k6/http'

import { check, sleep } from 'k6'

export const options = {
  stages: [
    { duration: '30s', target: 5000 },   // ramp-up
    { duration: '2m', target: 5000 },   // stable
    { duration: '30s', target: 0 },   // ramp-down
  ]
}

export default function () {
  const params = {
    timeout: '10s'
  }

  let response = http.get('http://localhost:8083/ciosp/bairros', params)

  check(response, { 'success ': (r) => r.status === 200 })

  sleep(1);
}