const formNode = document.getElementById("creationForm");
const addActorButton = document.getElementById("addActorButton")
const actorSelect = document.getElementById("actorSelect")

function generateOptionsFromArray(array) {
  const options = array.map(item => `<option>${item.id}. ${item.name}</option>`)
  return options.join("")
}

async function createActorsInSelect() {
  try {
    const res = await fetch("api/actor")
    const data = await res.json()

    const currentActors = actorSelect.getElementsByClassName("actor")
    let newActor = document.createElement("select")
    newActor.classList.add("actor")
    newActor.id = "" + currentActors.length + 1
    newActor.innerHTML = generateOptionsFromArray(data)
    actorSelect.appendChild(newActor)
  } catch (err) {
    console.error(err)
  }
}

addActorButton.addEventListener("click", () => {
  createActorsInSelect()
})

async function createActor() {
  const year = document.getElementById("year")
  const title = document.getElementById("title")
  const budget = document.getElementById("budget")
  const currentActors = actorSelect.getElementsByClassName("actor")
  let actors = []

  for (let i = 0; i < currentActors.length; i++) {
    const chosenActor = document.getElementById(currentActors.item(i).id).value
    const actorId = chosenActor.split(".")[0];

    try {
      const res = await fetch(`api/actor/${actorId}`)
      const data = await res.json()
      actors = [ ...actors, data ]
    } catch (err) {
      console.error(err)
    }
  }

  const movie = {
    year: year.value,
    title: title.value,
    actors: actors,
    budget: budget.value
  }

  try {
    const res = await fetch("api/movie", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(movie)
    });
    const content = await res.json();
    console.log(content)
    const success = document.getElementById("success")
    success.innerText = "Movie has successfully been created!"
  } catch (err) {
    console.error(err)
  }
}

formNode.addEventListener("submit", (e) => {
  e.preventDefault()
  createActor()
})