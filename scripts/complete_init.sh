#!/bin/bash

# Candy AI 数据库初始化完成脚本
echo "完成 Candy AI 数据库初始化..."

# 数据库配置
DB_NAME="candy_ai"
DB_USER="root"

# 提示用户输入MySQL root密码
echo "请输入MySQL root用户密码（如果无密码请直接按回车）："
read -s DB_PASSWORD

# 检查MySQL连接
echo "检查MySQL连接..."
if [ -z "$DB_PASSWORD" ]; then
    MYSQL_CMD="mysql -u$DB_USER"
else
    MYSQL_CMD="mysql -u$DB_USER -p$DB_PASSWORD"
fi

# 检查AI表是否存在，如果不存在则创建
echo "检查并创建AI相关表..."
$MYSQL_CMD $DB_NAME -e "SHOW TABLES LIKE 'ai_character';" | grep -q "ai_character"
if [ $? -ne 0 ]; then
    echo "AI表不存在，正在创建..."
    $MYSQL_CMD $DB_NAME < scripts/sql/ai_girlfriend_tables.sql
    if [ $? -eq 0 ]; then
        echo "AI相关表结构创建成功"
    else
        echo "错误：AI相关表结构创建失败"
        exit 1
    fi
else
    echo "AI相关表已存在，跳过创建"
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