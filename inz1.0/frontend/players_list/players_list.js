import {
    clearElementChildren,
    createLinkCell,
    createButtonCell,
    createTextCell,
    getParameterByName
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayPlayers();
});

/**
 * Fetches all users and modifies the DOM tree in order to display them.
 */
function fetchAndDisplayPlayers() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayPlayers(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/players', true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display users.
 *
 * @param {{characters: {id: number, name:string}[]}} characters
 */
function displayPlayers(players) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    players.players.forEach(player => {
        tableBody.appendChild(createTableRow(player));
    })
}

/**
 * Creates single table row for entity.
 *
 * @param {{id: number, name: string}} character
 * @returns {HTMLTableRowElement}
 */
function createTableRow(player) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(player.name));
    tr.appendChild(createLinkCell('edit', '../players_edit/players_edit.html?player=' + player.id));
    tr.appendChild(createButtonCell('delete', () => deleteCharacter(player.id)));
    return tr;
}

/**
 * Deletes entity from backend and reloads table.
 *
 * @param {number} character to be deleted
 */
function deleteCharacter(player) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayPlayers();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/players/' + player, true);
    xhttp.send();
}
