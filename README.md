## 启动
1. 基础环境, `docker-compose -f docker/docker-compose.dev.yml up`
2. 设置环境变量, 参考`.env.example`
3. 设置HOST, 参考`.host.example`
4. 修改`yml`配置参数
5. 设置参数`spring.profiles.active=dev`并启动

### 使用脚本
- `scripts/proxy.sh` 内网穿透(`仅支持MacOS`)
