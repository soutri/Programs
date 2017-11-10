#include <stdio.h>
#include <conio.h>
#include<string.h>
int main(){
char ch[]="g fmnc wms bgblr rpylqjyrc gr zw fylb. rfyrq ufyr amknsrcpq ypc dmp. bmgle gr gl zw fylb gq glcddgagclr ylb rfyr'q ufw rfgq rcvr gq qm jmle. sqgle qrpgle.kyicrpylq() gq pcamkkclbcb. lmu ynnjw ml rfc spj.";

int i;
for(i=0;i<strlen(ch);i++)
    if(ch[i]>='a'  && ch[i] <= 'z')
    {
        ch[i] = (ch[i]-'a' + 2 )%26 + 'a';
    }

printf("%s",ch);

//char  aplha[] = "abcdefghijklmnopqrstuvwxyz";
//char covert[100];
//for(i=0;i<26;i++) covert[i] = aplha[i]+2;

 }
