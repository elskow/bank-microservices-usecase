#!/usr/bin/env python3

import subprocess
import os
from concurrent.futures import ThreadPoolExecutor
import logging

def build_docker_image(module):
    logging.info(f"Building docker image for {module} asynchronously")
    subprocess.run(["./mvnw", "compile", "jib:dockerBuild", "-q"], cwd=module, check=True)

def find_modules_with_pom():
    modules = []
    for root, dirs, files in os.walk("../"):
        if "pom.xml" in files:
            modules.append(root)
    return modules

def main():
    logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(message)s")
    logging.info("Building docker images for all modules asynchronously")
    modules = find_modules_with_pom()
    with ThreadPoolExecutor() as executor:
        executor.map(build_docker_image, modules)
    logging.info("All docker images built successfully")

if __name__ == "__main__":
    main()