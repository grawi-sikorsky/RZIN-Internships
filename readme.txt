1. Setup postgresql database in docker by typing cmd command below:
	a) Add new container: $ docker run --name postgressbaza -e POSTGRES_PASSWORD=postgresspass -p 5432:5432 -d postgres
	b) If database is not created automaticaly by spring, add new database called "postgressbaza". Table schema should be
	    filled automaticaly.

2. Tests:
    a) pull localstack image:
        - $ docker pull localstack/localstack
        - or intellij image to pull: localstack/localstack
    b) for integrity testing please setup docker localstack with docker-compose.yml provided in /resources/docker/..
    c) after creating container could be neccessary to setup access/secretkey, to do so type in cmd:
        $: docker exec -it localstack_main bash
        $: aws configure
            (fill accesskey, secretkey, default_region to us-east-2 (access / secret / ue-east-2))
        $: aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name test-queue

    d) for testing localstack S3:
        - create bucket:        aws --endpoint-url=http://localhost:4566 s3 mb s3://test-bucket
        - list all buckets:     aws --endpoint-url=http://localhost:4566 s3 ls
        - list files on bucket: aws --endpoint-url=http://localhost:4566 s3 ls s3://test-bucket
