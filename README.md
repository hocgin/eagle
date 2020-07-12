## EAGLE
> STATUS: Development
- [Eagle UI](https://github.com/hocgin/eagle-ui)
- [Eagle](https://github.com/hocgin/eagle)

## Deploy
### Eagle
1. 基础环境, `docker-compose -f docker/docker-compose.dev.yml up`
2. 检查环境变量, 参考`.env.example`
3. 检查HOST, 参考`.host.example`
4. 修改`yml`配置参数
5. 设置参数`spring.profiles.active=dev`并启动`AppApplication`

**内网穿透**请参考 `scripts/proxy.sh` 内网穿透(`仅支持MacOS`)

Or `sh scripts/docker-compose.sh`
### Eagle UI
1. 基础环境，Node
2. `npm install`
3. `npm start`

## UI
<img src="http://cdn.hocgin.top/3A30AEEC-9D0B-40C1-B271-BCA9C37F7113.png" width="404" alt="UI"/>
