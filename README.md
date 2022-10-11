## Exercise 6 - To React, JAX-RS

#### Start taken from Exercise5.
* [x] Jetty is up and running
* [x] Serve dynamic content with Servlet
* [x] Fetch JSON from React to Servlet

#### New with Exercise 6:
* [x] React application with vite
* [ ] Framework support with JAX-RS
  * [ ] Build GET-request
  * [ ] Build POST-request

  
### React application with vite
#### Vite - alternative instead of parcel.
* Vite a little easier with debugging
* vite.config.js used more than package.json in parcel.

Code for vite-react (frontend) should be in: src/main
- cd src/main
- npm create vite@latest
  Set project name
  React -> Javascript

Then navigate to the new project folder
- cd <project-name>
- npm install

To get the frontend part to work with the backend (resource folder)
- package.json change:
  "dev": "vite build --watch"
- vite.config.js change:
  plugins: [react()],
  build: {
  outDir: "../resources/webapp"
  }

package.json: Now Vite builds instead of running on its own on its port.
--watch makes it continue to build.
vite.config.js: Changed output directory to resources/webapp
* Now backend - frontend is connected
    * Fetch JSON with react


#### Now to do JAX-RS
- Create a jerseyServlet.

Need to add dependency:
- jersey-container-servlet
- jersey-hk2
* Use same version on these!