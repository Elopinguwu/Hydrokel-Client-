const walk = require('walkdir');
var emitter = walk('.');
const fs = require('fs');
const path = require('path');
emitter.on('file', function(filename, stat) {
    if (path.extname(filename) == ".class") {
        fs.unlinkSync(path.resolve(filename));
        console.log('Removed  ', path.basename(filename));
    }
});