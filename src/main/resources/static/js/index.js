//-------------------------------------------------------------------------------------------
//------------------------------------ Global section  --------------------------------------
//-------------------------------------------------------------------------------------------
let roles = []
const basicUrl = 'http://localhost:8080/api/users/'

// void
async function fetchAllUsers(url) {
    const response = await fetch(url)
    const data = await response.json()
    if (data.length > 0) {
        let template = ``
        data.forEach(u => {
            let roles = ''
            u.roles.forEach(r => {
                roles += r.authority.substring(5) + ' '
            })

            template += `
                <tr id="tr${u.id}">
                    <td class="text-wrap">${u.id}</td>
                    <td class="text-wrap">${u.firstName}</td>
                    <td class="text-wrap">${u.lastName}</td>
                    <td class="text-wrap">${u.email}</td>
                    <td class="text-wrap">${roles}</td>
                    <td class="text-wrap">
                        <button type="button" class="btn btn-sm btn-info" onclick="openEditModal(${u.id})">
                        Edit</button></td>
                    <td class="text-wrap">
                        <button type="button" class="btn btn-sm btn-danger" onclick="openDeleteModal(${u.id})">
                        Delete</button></td>
                </tr>`
        })
        document.getElementById('tbody').innerHTML = template
        console.log(data)
        console.log('Table fetched!')
    }
}

// void
function fillTheRolesList(rolesArray, htmlElementId) {
    let rolesAsOptionsList = ''
    rolesArray.forEach((elem) => {
        rolesAsOptionsList += '<option>' + elem.authority.substring(5) + '</option>'
    })
    document.getElementById(htmlElementId).innerHTML = rolesAsOptionsList
}

// void
async function fetchPrincipal(url) {
    const response = await fetch(url)
    const data = await response.json()
    let email,
        roles = ''

    if (data != null) {
        data.roles.forEach(r => {
            roles += r.authority.substring(5) + ' '
        })
        email = data.email

        let template = ``
        template += `
            <tr id="tr${data.id}">
                <td class="text-wrap">${data.id}</td>
                <td class="text-wrap">${data.firstName}</td>
                <td class="text-wrap">${data.lastName}</td>
                <td class="text-wrap">${email}</td>
                <td class="text-wrap">${roles}</td>
            </tr>`
        document.getElementById('principalBody').innerHTML = template
        document.getElementById('titlePrincipal').innerHTML = `<b> ${email} </b>  with roles:  ${roles}`
    }
}

// returns roles-objects array (remove global var &(?) fillTheRolesList() from the body)
async function fetchRoles(url) {
    const response = await fetch(url)
    const data = await response.json()
    let allRoles = []

    if (data.length > 0) {
        data.forEach(r => {
            allRoles.push(r)
        })
        console.log('Roles fetched!')
    }

    console.log(allRoles)
    roles = allRoles
    fillTheRolesList(roles, 'onNewRoles')
    return allRoles
}

// void (roles ???) (remove global var from the body)
async function openEditModal(idToEdit) {
    fetch('/api/users/' + idToEdit)
        .then(result => result.json())
        .then(user => {
            document.getElementById("onEditId").value = idToEdit
            document.getElementById("onEditFirstName").value = user.firstName
            document.getElementById("onEditLastName").value = user.lastName
            document.getElementById("onEditEmail").value = user.email
            document.getElementById("onEditPassword").value = ''
            document.getElementById("submitEditBtn").setAttribute('onclick', `submitEdit(${idToEdit})`)

            fillTheRolesList(roles, 'onEditRoles')
            document.getElementById('onEditRoles').value = user.roles
        })

    $('#modalEdit').modal('show')
    console.log('...editBtn click-bound function fully executed.')
}

// void
async function openDeleteModal(idToDelete) {
    fetch('/api/users/' + idToDelete)
        .then(result => result.json())
        .then(user => {
            document.getElementById("onDeleteId").value = idToDelete
            document.getElementById("onDeleteFirstName").value = user.firstName
            document.getElementById("onDeleteLastName").value = user.lastName
            document.getElementById("onDeleteEmail").value = user.email
            document.getElementById("onEditPassword").value = ''
            fillTheRolesList(user.roles, 'onDeleteRoles')
            document.getElementById("submitDeleteBtn").setAttribute('onclick', `submitDelete(${idToDelete})`)
        })

    $('#modalDelete').modal('show')
    console.log('...deleteBtn click-bound function fully executed.')
}

// void (basicUrl!)
async function submitDelete(id) {
    fetch(basicUrl + id, {method: 'DELETE'})
        .then(res => res.text())
        .then(() => $('#tr' + id).remove())
    $('#modalDelete').modal('hide')
    console.log('...submitDeleteBtn click-bound function fully executed.')
}

// void (basicUrl! + ROLES!)
async function submitEdit(id) {
    const user = {
        id: id,
        firstName: document.getElementById("onEditFirstName").value,
        lastName: document.getElementById("onEditLastName").value,
        email: document.getElementById("onEditEmail").value,
        password: document.getElementById("onEditPassword").value,
        roles: {
            id: 2,
            authority: 'ROLE_USER'
        }                                                           // ACHTUNG!!!!!!!
    }
    console.log(user)

    const requestCfg = {
        method: 'PUT',
        body: user,
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }

    console.log(requestCfg)
    fetch(basicUrl + id, requestCfg)
        .then(res => res.json())
        .then(data => console.log(data))
    // .then(() => initialisation('http://localhost:8080/api/users'))     // BUUUUUUUUUUUUUUU
    $('#modalEdit').modal('hide')
    console.log('...submitEditBtn click-bound function fully executed.')

}

// void (basicUrl! + ROLES!)
async function addNewUser() {
    const newUser = {
        firstName: document.getElementById("onNewFirstName").value,
        lastName: document.getElementById("onNewLastName").value,
        email: document.getElementById("onNewEmail").value,
        password: document.getElementById("onNewPassword").value,
        roles: [{
            id: 2,
            authority: 'ROLE_USER'
        }]                                                           // ACHTUNG!!!!!!!
    }

    console.log(newUser)
    fetch(basicUrl, {
        method: 'POST',
        body: newUser,
        headers: {
            'content-type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(data => console.log(data))

    // document.getElementById("onNewFirstName").value = ''
    // document.getElementById("onNewLastName").value = ''
    // document.getElementById("onNewEmail").value = ''
    // document.getElementById("onNewPassword").value = ''

    console.log('...newBtn click-bound function fully executed.')
}

// void
async function refreshMainInfo(baseUrl) {
    await fetchAllUsers(baseUrl)
}

//-------------------------------------------------------------------------------------------
//-------------------------------------- Main section  --------------------------------------
//-------------------------------------------------------------------------------------------

$(document).ready(function () {
    fetchRoles('http://localhost:8080/api/roles')
        .then(() => fetchPrincipal('http://localhost:8080/api/user'))
        .then(() => refreshMainInfo(basicUrl))

    console.log('Loaded!')
})





