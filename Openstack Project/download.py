import os
os,system("mkdir /var/cache/archives")
os.system("mkdir /var/cache/config")
os.system("wget -O /var/cache/archives/archives.tar.gz http://bit.ly/1POJARG")
os.system("wget -O /var/cache/config/config.tar.gz http://bit.ly/1nkBGJ1")
os.system("tar -xzf /var/cache/archives/archives.tar.gz")
os.system("tar -xzf /var/cache/config/config.tar.gz")
