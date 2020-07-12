const mongoose = require('mongoose');

const userSchema = mongoose.Schema({

    kodeBuku: {
        type: String
    },
    judulBuku: {
        type: String
    },
    penerbit: {
        type: String
    },
    pengarang: {
        type: String
    },
    tahunTerbit: {
        type: String
    },
    hargaBuku: {
        type: String
    },
    gambar: {
        type: String
    }
})
module.exports = mongoose.model('buku', userSchema)