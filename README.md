for the first part of the task which is " create an interceptor that will block the ip address from using the endpoint if he exceeds 10 requests/min" 
i went with a soultion using Bucket4j library  - used in RateLimitingInterceptor class-

for the second part of the task which is to create an interceptor that will block the ip address if he exceeds 500 requests/30min over all my endpoints i went with a hard coded soultion without using 
libraries which is seen in "RateLimiterInterceptor"

you can also see that the two classes have similar names well that because both of them can do the same job for the other but i went with two classes to explore more options and ways to solve this problem.
