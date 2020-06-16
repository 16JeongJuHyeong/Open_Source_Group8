import socket
from MLP import model, np, class_names

count = 0;
host = '192.168.0.10'  # Symbolic name meaning all available interfaces
port = 9999  # Arbitrary non-privileged port

server_sock = socket.socket(socket.AF_INET)
server_sock.bind((host, port))
server_sock.listen(1)

print("기다리는 중")
client_sock, addr = server_sock.accept()


print('Connected by', addr)

# 서버에서 "안드로이드에서 서버로 연결요청" 한번 받음
data = client_sock.recv(1024)
print(data.decode("utf-8"), len(data))

while (True):
    # 안드로이드에서 값 받으면 "하나받았습니다 : 숫자" 보낼 것 받음
    data = client_sock.recv(1024)
    data1 = data.decode('utf-8')
    data1 = data1.replace("[", "")
    data1 = data1.replace("]", "")
    data1 = data1.replace(",", "")
    msp = data1.split()
    input = list(map(int, msp))
    count += 1
    print('Received', input)
    X_test = np.array([input])
    predictions = model.predict(X_test)
    result = class_names[np.argmax(predictions)]
    print(result)
    client_sock.send(result.encode())



# 연결끊겠다는 표시 보냄
# i=99
# client_sock.send(i.to_bytes(4, byteorder='little'))

client_sock.close()
server_sock.close()