import os
import shutil
print "updating your system"
os.system("sudo apt-get update -y && apt-get upgrade -y && apt-get dist-upgrade")
print " update complete"
print "Now installing ntp"
os.system("sudo apt-get install -y ntp")
os.system("sed -i 's/server ntp.ubuntu.com/server 10.0.0.11/g' /etc/ntp.conf")
os.system("service ntp restart")
os.system("sudo apt-get install -y cpu-checker")
os.system("sudo apt-get install -y kvm libvirt-bin pm-utils")
os.system("sudo apt-get install -y nova-compute-kvm python-guestfs")
os.system("dpkg-statoverride --update --add root root 0644 /boot/vmlinuz-$(uname -r)")
#os.system("wget "https://drive.google.com/file/d/0B180gqY_dIzMa0FobFNCVzFxdWs" statoverride.txt");
shutil.copyfile("/home/soutri/Desktop/config/statoverride", "/etc/kernel/postinst.d/statoverride")
os.system("chmod +x /etc/kernel/postinst.d/statoverride")
#os.system("wget------------------------")
os.system("cp /home/soutri/Desktop/config/nova.conf /etc/nova/nova.conf")
os.system("rm /var/lib/nova/nova.sqlite")
os.system("service nova-compute restart")
#os.system("wget------------------------")
os.system("cp /home/soutri/Desktop/config/sysctl.conf /etc/sysctl.conf")
os.system("sysctl -p")
os.system("apt-get install -y neutron-common neutron-plugin-ml2 neutron-plugin-openvswitch-agent")
#os.system("wget------------------------")
os.system("cp /home/soutri/Desktop/config/neutron.conf /etc/neutron/neutron.conf")
#os.system("wget------------------------")
os.system("cp /home/soutri/Desktop/config/ml2_conf.ini /etc/neutron/plugins/ml2/ml2_conf.ini")
os.system("service openvswitch-switch restart")
os.system("ovs-vsctl add-br br-int")
#os.system("wget-----------")
os.system("cp /home/soutri/Desktop/config/nova.conf /etc/nova/nova.conf")
os.system("service nova-compute restart")
os.system("service neutron-plugin-openvswitch-agent restart")
os.system("nova-manage service list")
print "done"










