@echo off
chcp 65001 >nul

rem 项目名称
set project=su-starter

rem 克隆项目远程地址
set clonePath=git@gitee.com:umb

rem 标题
title 批处理【%project%】项目,时间:%DATE% %time%

rem 菜单选项
:again
echo --------------------------------------------------------------
echo %project%项目快捷处理命令:
echo --------------------------------------------------------------
echo 1、一键上传 [%project%].
echo --------------------------------------------------------------
set /p num=请输入选择并按回车：

rem Main仓库地址
set MainPath= git clone %clonePath%/%project%.git
set MainCatalog= cd %project%

rem Admin仓库地址及安装依赖
set AdminPath= git clone %clonePath%/%project%Admin.git
set AdminInstall= npm install --registry=https://registry.npm.taobao.org
set AdminCatalog= cd %project%Admin

rem APP仓库地址
set AppPath= git clone %clonePath%/%project%APP.git
set AppCatalog= cd %project%APP

rem 返回上级目录
set Back= cd ..

if "%num%"=="1" goto push
echo 批处理自动退出.
exit 

 
:push
cls
echo ====================================
echo 正在执行上传 [%project%].
echo ====================================
set /p msg=请输入提交的描述信息并按回车：
echo %msg%
git add .
git commit -m "%msg%"
git push
%Back%
echo ====================================
echo 执行完成
echo ====================================
pause
goto again