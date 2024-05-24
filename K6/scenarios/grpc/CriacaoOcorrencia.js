import grpc from "k6/net/grpc";
import { check, sleep } from "k6";

const client = new grpc.Client();

client.load(['.'], 'GerarOcorrenciaBFF.proto');

export const options = {
    insecureSkipTLSVerify: true,
    stages: [
        { duration: '30s', target: 100 },   // ramp-up
        { duration: '4m', target: 100 },   // stable
        { duration: '30s', target: 0 },   // ramp-down
    ]
}

export default () => {
    if(__ITER === 0){
        client.connect('localhost:9091', {
            plaintext: true
        });
    }
    
    const payload = { 
        cpfUsuario:"76077943010",
        latitude: -5.80784800,
        longitude: -35.20229400
    };

    // <package>.<service>/<procedure>
    const response = client.invoke('br.com.cad.proto_content.GerarOcorrenciaService/gerarOcorrenciaBFF', payload);

    check(response, {
      'status is OK': (r) => r && r.status === grpc.StatusOK
    });

    // console.log(JSON.stringify(response.message));
    // client.close();
    sleep(1);
};