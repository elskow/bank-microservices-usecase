#!/bin/bash

set -e
echo "Building docker images for all modules asynchronously"
pids=()

while IFS= read -r module; do
  echo "Building docker image for $module asynchronously"
  (cd "$module" && ./mvnw compile jib:dockerBuild -q) &
  pids+=("$!")
done < <(find . -name "pom.xml" -exec dirname {} +)

for pid in "${pids[@]}"; do
    wait "$pid"
done

echo "All docker images built successfully"