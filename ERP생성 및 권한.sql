--������̸�c##���ְ� �����ϱ�
ALTER SESSION SET "_ORACLE _SCRIPT"=TRUE;

--����� ����
CREATE USER ����ڸ� IDENTIFIED BY ���;

--�����ͺ��̽� �������� �ֱ�
GRANT CREATE SESSION TO ����ڸ�;

--���̺���� ��ɻ�� �����ֱ�
GRANT CREATE TABLESPACE TO ����ڸ�;
GRANT CREATE TABLE TO ����ڸ�;
GRANT CREATE SEQUENCE TO ����ڸ�;
GRANT CREATE VIEW TO ����ڸ�;
GRANT CREATE SYNONYM TO ����ڸ�;

--�׸�鿡 ���� ������ ����ڿ��� �ο��ϱ�(DBA�� ���±���)
GRANT RESOURCE, CONNECT, UNLIMITED TABLESPACE TO ����ڸ�;
GRANT DBA TO ����ڸ�;


