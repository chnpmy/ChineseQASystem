#include <iostream>
#include <stdio.h>
#include <string>
#include <string.h>
#include <math.h>
#include <fstream>
using namespace std;

ifstream fin("dic.txt");
ifstream fin1("id.txt");
ifstream fin2("doc.txt");
ifstream fin3("sentence.txt");
ofstream fout("ans.txt");

const int maxw = 80000;
string str[maxw];

struct words{
       int cnt;
       char id[20][10];
       }words[maxw];

struct hashlist{
       int v,next;
       }hash[maxw];

int s1[30],s2[30][30],s3[30][30][100],s4[30][30][100][30];

int first[maxw],tot;
void hashadd(int u,int v)
{
     ++tot;hash[tot].v=v;hash[tot].next=first[u];first[u]=tot;
}

string sentence[100];
int len;

int calcp(string s)
{
    int len = s.size(),i,p = 0;
    for(i = 0;i < len;i++)
    p = (p*97%maxw + s[i]%maxw)%maxw;
    for(i = 0;i < len;i++)
    p = (p*97%maxw + s[i]*s[i]%maxw)%maxw;
    p = (p+maxw)%maxw;
    return p;
}
double calcsin(int v1,int v2)
{
       return cos(((double)v1)*M_PI/180)*((double)(v1-v2+1))/((double)v1);
}
int hashget(string s)
{
    int p = calcp(s);
    int i,v;
    for(i = first[p];i!=0;i = hash[i].next){
          v = hash[i].v;
          if(s == str[v])return v;
    }
    return 0;
}
void loading_dic()
{
     int i,j,p;
     for(i = 1;i <= 77492; i++){
           fin >> str[i];
           fin >> words[i].cnt;
           for(j = 1; j <= words[i].cnt; j++)
           fin >> words[i].id[j];
           p = calcp(str[i]);
           hashadd(p,i);
     }
}
int trans(char s[],int k)
{
    if(k == 1)return s[0]-'A'+1;
    if(k == 2)return s[1]-'a'+1;
    if(k == 3)return (s[2]-'0')*10+(s[3]-'0');
    if(k == 4)return s[4]-'A'+1;
    if(k == 5)return (s[5]-'0')*10+(s[6]-'0');
}
void loading_id()
{
     int i;
     char s[10];
     int d1,d2,d3,d4,d5;
     for(i = 1;i <= 17817; i++){
           fin1 >> s;
           d1 = trans(s,1);
           d2 = trans(s,2);
           d3 = trans(s,3);
           d4 = trans(s,4);
           d5 = trans(s,5);
           s1[d1] = max(s1[d1],d2);
           s2[d1][d2] = max(s2[d1][d2],d3);
           s3[d1][d2][d3] = max(s3[d1][d2][d3],d4);
           s4[d1][d2][d3][d4] = max(s4[d1][d2][d3][d4],d5);
           }
}
double calcs(char id1[],char id2[])
{
       int v1,v2;
       v1 = trans(id1,1);v2 = trans(id2,1);
       if(v1 != v2)return 0.1;
       v1 = trans(id1,2);v2 = trans(id2,2);
       if(v1 != v2)return 0.65*calcsin(s1[trans(id1,1)],abs(v1-v2));
       v1 = trans(id1,3);v2 = trans(id2,3);
       if(v1 != v2)return 0.8*calcsin(s2[trans(id1,1)][trans(id1,2)],abs(v1-v2));
       v1 = trans(id1,4);v2 = trans(id2,4);
       if(v1 != v2)return 0.9*calcsin(s3[trans(id1,1)][trans(id1,2)][trans(id1,3)],abs(v1-v2));
       v1 = trans(id1,5);v2 = trans(id2,5);
       if(v1 != v2)return 0.95*calcsin(s4[trans(id1,1)][trans(id1,2)][trans(id1,3)][trans(id1,4)],abs(v1-v2));
       if(id1[7] == '#')return 0.5;
       return 1;
}
double calc_word_sim(string word1,string word2)
{
     int x1,x2;
     x1 = hashget(word1);
     x2 = hashget(word2);
     if(word1 == word2)return 1.5;
     double s = 0,u;
     int i,j;
     for(i = 1;i <= words[x1].cnt;i++)
     for(j = 1;j <= words[x2].cnt;j++){
           u = calcs(words[x1].id[i],words[x2].id[j]);
           if(u > s)s = u;
           }
     return s;
}
double calc_sentence_sim(string s1[],string s2[],int len1,int len2)
{
       int i,j;
       double sum = 0;
       for(i = 1;i <= len1;i++){
             double s = 0,u;
             for(j = 1; j <= len2;j++){
                   u = calc_word_sim(s1[i],s2[j]);
                   if(u>s)s=u;
                   }
             sum = sum + s;
       }
       return sum;
}
void loading_sentence()
{
     fin3 >> len;
     int i;
     for(i = 1;i <= len;i++)
     fin3 >> sentence[i];
}
int total;
string doc[10000][100];
int l[10000];
void work()
{
     fin2 >> total;
     int i,j;
     int k = 0;
     double sim,maxsim = 0;
     for(i = 1;i <= total;i++){
           fin2 >> l[i];
           for(j = 1;j <= l[i];j++)
           fin2 >> doc[i][j];
           sim = calc_sentence_sim(sentence,doc[i],len,l[i]);
           if(sim > maxsim){maxsim = sim;k = i;}
     }
     for(i = 1;i <= l[k];i++)
     fout << doc[k][i] << " ";
}
int main(void)
{
    cout<<"Loading data..."<<endl;
    loading_dic();
    loading_id();
    loading_sentence();
    cout<<"Loading complete..."<<endl;
    cout<<"Searching..."<<endl;
    work();
    cout<<"Searching complete..."<<endl;
    system("pause");
    return 0;
}
