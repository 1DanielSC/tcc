import grpc from "k6/net/grpc";
import { sleep } from "k6";

const client = new grpc.Client();

client.load(['.'], 'Bairro.proto');

export const options = {
    stages: [
      { duration: '30s', target: 5000 },   // ramp-up
      { duration: '2m', target: 5000 },   // stable
      { duration: '30s', target: 0 },   // ramp-down
    ]
}

export default () => {
    const params = {
        timeout: '10s'
    }

    client.connect('localhost:9090', {
        plaintext: true
    });
    
    const stream = new grpc.Stream(
        client, 
        'br.com.cad.proto_content.BairroGrpcService/listarBairrosStreaming',
        params
    );
    
    stream.on('data', (data) => {
    });

    stream.on('end', () => {
        client.close();
    });

    stream.on('error', function (e) {
    });

    stream.write({});
    stream.end();

    sleep(1);
};