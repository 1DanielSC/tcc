import http from 'k6/http'

import { check, sleep } from 'k6'

export const options = {
  stages: [
    { duration: '30s', target: 200 },   // ramp-up
    { duration: '4m', target: 200 },   // stable
    { duration: '30s', target: 0 },   // ramp-down
  ]
}

export default function () {

  const url = 'http://localhost:8085/bff-salveelas/ocorrencias/evento';
  const params = {
      headers: { 'Content-Type': 'application/json' },
  }
  const payload = {
    cpfUsuario: "76077943010",
    latitude: -5.80784800,
    longitude: -35.20229400
  }

  let response = http.post(url, JSON.stringify(payload), params)

  check(response, { 'success login': (r) => r.status === 200 })

  sleep(1)

}