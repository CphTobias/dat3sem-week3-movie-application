const allTableNode = document.getElementById("allTable")
const allButtonNode = document.getElementById("allButton")

function createMovieTableFromArray(array) {
  const rows = array.map(item => `
<tr>
  <td>${item.id}</td>
  <td>${item.year}</td>
  <td>${item.title}</td>
  <td>
  <table>
        <thead>
          <th scope="col">Id</th>
          <th scope="col">Name</th>
          <th scope="col">Age</th>
        </thead>
        <tbody>
        ${item.actors.map(actor => `
          <tr>
            <td>Id: ${actor.id}</td>
            <td>Name: ${actor.name}</td>
            <td>Age: ${actor.age}</td>
          </tr>
      `).join("")}
        </tbody>
 </table>
 </td>
</tr>`).join("")

  return `
    <thead>
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Year</th>
        <th scope="col">Title</th>
        <th scope="col">Actors</th>
      </tr>
    </thead>
    <tbody>${rows}</tbody>`
}

async function generateAllMovies(url) {
  try {
    const res = await fetch(url)
    const data = await res.json()
    allTableNode.innerHTML = createMovieTableFromArray(data)
  } catch (err) {
    console.error(err)
  }
}

allButtonNode.onclick = () => {
  generateAllMovies("api/movie")
}

const oneMovieInputNode = document.getElementById("oneMovieInput")
const oneMovieButtonNode = document.getElementById("oneMovieButton")
const oneMovieTableNode = document.getElementById("oneMovieTable")

function createMovieTable(movie) {
  return `
  <thead>
    <tr>
      <th scope="col">Id</th>
      <th scope="col">Year</th>
      <th scope="col">Title</th>
      <th scope="col">Actors</th>
    </tr>
  </thead>
  <tr>
    <tbody>
      <td>${movie.id}</td>
      <td>${movie.year}</td>
      <td>${movie.title}</td>
      <td>
      <table>
        <thead>
          <th scope="col">Id</th>
          <th scope="col">Name</th>
          <th scope="col">Age</th>
        </thead>
        <tbody>
          ${movie.actors.map(actor => `
          <tr>
            <td>Id: ${actor.id}</td>
            <td>Name: ${actor.name}</td>
            <td>Age: ${actor.age}</td>
          </tr>
          `).join("")}
        </tbody>
      </table>
      </td>
    </tbody>
  </tr>`
}

async function generateOneMovie(url) {
  try {
    const res = await fetch(url)
    const data = await res.json()
    oneMovieTableNode.innerHTML = createMovieTable(data)
  } catch (err) {
    oneMovieTableNode.innerHTML = "<h3>Unknown id please provide a valid one</h3>"
  }
}

oneMovieButtonNode.onclick = () => {
  const movieId = oneMovieInputNode.value
  generateOneMovie(`api/movie/${movieId}`)
}