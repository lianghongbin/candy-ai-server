#!/bin/bash

# Candy AI 数据库初始化脚本
echo "开始初始化 Candy AI 数据库..."

# 数据库配置
DB_NAME="candy_ai"
DB_USER="root"

# 提示用户输入MySQL root密码
echo "请输入MySQL root用户密码（如果无密码请直接按回车）："
read -s DB_PASSWORD

# 检查MySQL连接
echo "检查MySQL连接..."
if [ -z "$DB_PASSWORD" ]; then
    # 无密码连接
    if ! mysql -u$DB_USER -e "SELECT 1;" > /dev/null 2>&1; then
        echo "错误：无法连接到MySQL，请检查："
        echo "1. MySQL服务是否正在运行"
        echo "2. 用户名和密码是否正确"
        exit 1
    fi
    MYSQL_CMD="mysql -u$DB_USER"
else
    # 有密码连接
    if ! mysql -u$DB_USER -p$DB_PASSWORD -e "SELECT 1;" > /dev/null 2>&1; then
        echo "错误：无法连接到MySQL，请检查："
        echo "1. MySQL服务是否正在运行"
        echo "2. 用户名和密码是否正确"
        exit 1
    fi
    MYSQL_CMD="mysql -u$DB_USER -p$DB_PASSWORD"
fi

# 创建数据库
echo "创建数据库 $DB_NAME..."
$MYSQL_CMD -e "CREATE DATABASE IF NOT EXISTS $DB_NAME CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

if [ $? -eq 0 ]; then
    echo "数据库 $DB_NAME 创建成功"
else
    echo "错误：数据库创建失败"
    exit 1
fi

# 导入基础表结构（若依系统表）
echo "导入基础表结构..."
$MYSQL_CMD $DB_NAME < scripts/sql/ry_20250522.sql

if [ $? -eq 0 ]; then
    echo "基础表结构导入成功"
else
    echo "错误：基础表结构导入失败"
    exit 1
fi

# 导入定时任务表
echo "导入定时任务表..."
$MYSQL_CMD $DB_NAME < scripts/sql/quartz.sql

if [ $? -eq 0 ]; then
    echo "定时任务表导入成功"
else
    echo "错误：定时任务表导入失败"
    exit 1
fi

# 导入AI相关表结构
echo "导入AI相关表结构..."
$MYSQL_CMD $DB_NAME < scripts/sql/ai_girlfriend_tables.sql

if [ $? -eq 0 ]; then
    echo "AI相关表结构导入成功"
else
    echo "错误：AI相关表结构导入失败"
    exit 1
fi

echo "数据库初始化完成！"
echo "数据库名称: $DB_NAME"
echo "默认管理员账号: admin"
echo "默认管理员密码: admin123"
echo ""
echo "请确保 application-druid.yml 中的数据库配置正确："
echo "- 数据库URL: jdbc:mysql://localhost:3306/$DB_NAME"
echo "- 用户名: $DB_USER"
if [ -z "$DB_PASSWORD" ]; then
    echo "- 密码: (无密码)"
else
    echo "- 密码: $DB_PASSWORD"
fi 