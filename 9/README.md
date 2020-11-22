# Assignment 9

## _Reading task 4_

The "twelve-factor app" is a name of methodology for building modern software services and applications.

It can be seen as a quality-standard set of recommendations for developers.

### _Your task is to_

- research Internet sources discussing these recommendations
- based on your findings, try to re-order (re-number) the known factors according selected priority criteria
- be ready to argument these criteria in class

---

### _The 12 factors - reordered_

## 1. Codebase

    Use source control. One codebase per application. Deploy to multiple environments.

    - this is a no-brainer. Keeping different versions in folders by timestamps or something similar is a disastrous approach. Moreover, using source control opens up endless possibilities of experimenting without the risk of not being able to revert. Or rollback a faulty build. Keeping things apart is much in sync with dividing and conquering.

## 5. Build, Release, Run

    Deploy apps in three discrete steps: build (convert codebase into executable), release (combine build artifacts with config to create a release image), and run (use the same release image every time you launch).

## 10. Dev/prod parity

    Keep the development environment identical to all other environments.
    - minimizing the risk of "it works on my machine"-scenarios is of substantial importance when working in DevOps (CI/CD) scenarios.

## 12. Admin Processes

    Run admin tasks (database migrations, background jobs, cache clearing, and so on) as one-off, isolated processes.

## 3. Config

    Keep configuration separate from codebase.

## 2. Dependencies

    Declare and isolate dependencies. Never rely on the existence of system packages. Never commit dependencies in the codebase repository.

## 4. Backing Services

    Treat services the app consumes (database, caching, and so on) as attachable resources. You should be able to swap your database instance without code changes.

## 6. Processes

    Processes should be stateless.

## 7. Port Binding

    Export services via port binding. Apps should be completely self-contained.

## 8. Concurrency

    Scale out by decomposing applications into individual processes that do specific jobs.

## 9. Disposability

    Apps should be quick to start, resilient to failure, and graceful to shut down. Expect servers to fail, be added, change.

## 11. Logs

    Treat logs as events streams. Write to stdout and stderr.

- [https://dev.to/heroku/twelve-factor-apps-a-retrospective-and-look-forward-4j4f](https://dev.to/heroku/twelve-factor-apps-a-retrospective-and-look-forward-4j4f)
- [https://12factor.net/](https://12factor.net/)
- [https://will.koffel.org/post/2014/12-factor-apps-in-plain-english/](https://will.koffel.org/post/2014/12-factor-apps-in-plain-english/)
- [https://medium.com/hashmapinc/how-i-use-the-twelve-factor-app-methodology-for-building-saas-applications-with-java-scala-4cdb668cc908](https://medium.com/hashmapinc/how-i-use-the-twelve-factor-app-methodology-for-building-saas-applications-with-java-scala-4cdb668cc908)
