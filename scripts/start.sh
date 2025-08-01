#!/bin/bash

# Candy AI 启动脚本
# 支持开发环境(dev)和生产环境(prod)

# 默认环境
DEFAULT_PROFILE="dev"

# 显示帮助信息
show_help() {
    echo "Candy AI 启动脚本"
    echo ""
    echo "用法: $0 [选项]"
    echo ""
    echo "选项:"
    echo "  -p, --profile PROFILE    指定环境 (dev|prod), 默认: $DEFAULT_PROFILE"
    echo "  -h, --help              显示此帮助信息"
    echo ""
    echo "示例:"
    echo "  $0                    # 使用默认环境(dev)启动"
    echo "  $0 -p dev            # 启动开发环境"
    echo "  $0 -p prod           # 启动生产环境"
    echo ""
}

# 检查环境变量
check_environment() {
    local profile=$1
    
    echo "检查 $profile 环境配置..."
    
    if [ "$profile" = "prod" ]; then
        echo "生产环境检查:"
        echo "- 请确保已设置必要的环境变量:"
        echo "  DB_HOST, DB_PASSWORD, REDIS_HOST, REDIS_PASSWORD"
        echo "- 或者修改 application-prod.yml 中的配置"
        echo ""
        read -p "是否继续启动? (y/N): " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            echo "启动已取消"
            exit 0
        fi
    fi
}

# 启动应用
start_application() {
    local profile=$1
    
    echo "启动 Candy AI 应用 (环境: $profile)..."
    echo ""
    
    # 检查Java环境
    if ! command -v java &> /dev/null; then
        echo "错误: 未找到Java环境，请先安装Java"
        exit 1
    fi
    
    # 检查Maven环境
    if ! command -v mvn &> /dev/null; then
        echo "错误: 未找到Maven环境，请先安装Maven"
        exit 1
    fi
    
    # 检查项目目录
    if [ ! -f "pom.xml" ]; then
        echo "错误: 请在项目根目录下运行此脚本"
        exit 1
    fi
    
    # 启动应用
    echo "使用 Maven 启动应用..."
    mvn spring-boot:run -pl ruoyi-admin -Dspring.profiles.active=$profile
}

# 主函数
main() {
    local profile=$DEFAULT_PROFILE
    
    # 解析命令行参数
    while [[ $# -gt 0 ]]; do
        case $1 in
            -p|--profile)
                profile="$2"
                shift 2
                ;;
            -h|--help)
                show_help
                exit 0
                ;;
            *)
                echo "未知选项: $1"
                show_help
                exit 1
                ;;
        esac
    done
    
    # 验证环境参数
    if [ "$profile" != "dev" ] && [ "$profile" != "prod" ]; then
        echo "错误: 无效的环境参数 '$profile'，支持的环境: dev, prod"
        exit 1
    fi
    
    # 检查环境配置
    check_environment $profile
    
    # 启动应用
    start_application $profile
}

# 运行主函数
main "$@" 