# mendel-challenge

## Run application

### Requeriments

- You must have docker installed

### Build image

Place your terminal on Dockerfile's path and run:

``` bash
docker build -t mendel-challenge . 
```

### Run application

```bash
docker run -p 8080:8080 -t mendel-challenge
```

### Access swagger

Open http://localhost:8080/swagger on your browser