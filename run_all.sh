#!/bin/bash

# Detect OS
OS="$(uname)"
PROJECT_DIR="$(pwd)"

# Kill all processes using port 5000 and 5001
for port in 5000 5001; do
    PID=$(lsof -t -i :$port)
    if [ -n "$PID" ]; then
        echo "Killing process $PID on port $port"
        kill -9 $PID
    else
        echo "No process on port $port"
    fi
done

if [[ "$OS" == "Darwin" ]]; then
    # macOS - use Terminal.app
    echo "Running on macOS..."

    osascript <<EOF
tell application "Terminal"
    do script "cd $PROJECT_DIR && mvn -pl router exec:java"
    do script "cd $PROJECT_DIR && mvn -pl broker exec:java"
    do script "cd $PROJECT_DIR && mvn -pl market exec:java"
end tell
EOF

elif [[ "$OS" == "Linux" ]]; then
    # Linux (Ubuntu) - use gnome-terminal
    echo "Running on Linux..."

    gnome-terminal -- bash -c "cd $PROJECT_DIR && mvn -pl router exec:java; exec bash"
    gnome-terminal -- bash -c "cd $PROJECT_DIR && mvn -pl router exec:java; exec bash"
    gnome-terminal -- bash -c "cd $PROJECT_DIR && mvn -pl market exec:java; exec bash"

else
    echo "Unsupported OS: $OS"
    exit 1
fi