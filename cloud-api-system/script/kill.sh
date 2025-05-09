# gate
PORT=8080
PID=$(lsof -ti :$PORT)

if [ -z "$PID" ]; then
  echo "端口 $PORT 无进程运行"
else
  kill -9 $PID
  echo "已终止端口 $PORT 的进程: $PID"
fi

# mock
PORT=8081
PID=$(lsof -ti :$PORT)

if [ -z "$PID" ]; then
  echo "端口 $PORT 无进程运行"
else
  kill -9 $PID
  echo "已终止端口 $PORT 的进程: $PID"
fi

# user
PORT=8082
PID=$(lsof -ti :$PORT)

if [ -z "$PID" ]; then
  echo "端口 $PORT 无进程运行"
else
  kill -9 $PID
  echo "已终止端口 $PORT 的进程: $PID"
fi