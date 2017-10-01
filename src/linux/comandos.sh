#####utiles linux

#Archivos .tar.gz:
#Comprimir: 
tar -czvf empaquetado.tar.gz /carpeta/a/empaquetar/
#Descomprimir: 
tar -xzvf archivo.tar.gz

#Archivos .tar:
#Empaquetar: 
tar -cvf paquete.tar /dir/a/comprimir/
#Desempaquetar: 
tar -xvf paquete.tar

#Archivos .gz:
#Comprimir: 
gzip -9 index.php
#Descomprimir: 
gzip -d index.php.gz
#ver archivos
tar -tf archivo.tar

#Archivos .zip:
#Comprimir: 
zip archivo.zip carpeta
#Descomprimir: 
unzip archivo.zip

#cambiar la hora por terminal
date --set "2014-01-28 15:00"

#edison reparar archivos da�ados read
e2fsck /dev/root

#ver conexiones activas en postgres
netstat -tupan | grep 5432 | grep ESTA | wc -l
#20

#abrir puerto en iptables
iptables -A INPUT -p tcp --dport 5050 -m state --state NEW -j ACCEPT

update-rc.d DemonioLeer3G defaults

pgrep -l -f java #comandos linux para procesos

#setear variable de entorno
export VARIABLE_ENTORNO=mi_variable
#eliminar variable de entorno
unset VARIABLE_ENTORNO

#chkconfig
chkconfig --level 35 mysql off

#PARA DESBLOQUEAR UNA IP USA EL SIGUIENTE EJEMPLO
iptables -I INPUT -s 189.216.139.148 -j ACCEPT

#PARA BLOQUEAR UNA IP PUEDES USAR ESTE EJEMPLO
iptables -I INPUT -s 189.216.139.148 -j DROP

#PARA ELIMINAR UNA REGLA DE LAS TABLAS IP
iptables -F

#PARA LISTAR LAS REGLAS DE LAS TABLAS IP
iptables -L -n

#ver ip no incluyendo de loopback
hostname -I

#ver mac conectadas y su direccion IP
arp -n

#volver al directorio anterior
cd -