/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var colors = [
    [255, 105, 0],
    [0, 0, 555],
    [64, 105, 8],
    [115, 147, 48],
    [151, 174, 44],
    [175, 166, 37],
    [230, 202, 95],
    [249, 207, 61],
    //others
    [64, 105, 8],
    [74, 255, 0],
    [255, 255, 0],
    [64, 105, 8],
    [37, 255, 0],
    [62, 105, 8],
    [64, 108, 8],
    [64, 105, 8],
    [64, 105, 8],
    [255, 105, 11],
    [255, 255, 0],
    [255, 108, 165],
    [64, 105, 0],
    [32, 255, 0],
    [255, 255, 8],
    [64, 108, 8],
    [255, 105, 0],
    [4, 255, 0],
    [0, 7, 0],
    [0, 0, 0],
    [255, 255, 8],
    [255, 5, 0],
    [0, 0, 0],
    [64, 0, 0],
    [243, 0, 0],
    [255, 105, 0],
    [0, 7, 0],
    [0, 7, 0],
    [255, 108, 0],
    [84, 255, 0],
    [255, 108, 0],
    [255, 92, 0],
    [0, 224, 0],
    [255, 0, 0],
    [243, 0, 0],
    [64, 66, 0],
    [255, 92, 0],
    [0, 224, 0],
    [0, 255, 0],
    [243, 255, 0],
    [255, 108, 0],
    [217, 255, 8],
    [64, 0, 0],
    [255, 255, 8],
    [57, 255, 0],
    [59, 255, 0],
    [64, 105, 8],
    [59, 255, 0],
    [64, 0, 0],
    [0, 0, 0],
    [255, 105, 0],
    [255, 89, 0],
    [64, 105, 8],
    [255, 108, 8],
    [64, 105, 0],
    [255, 255, 246],
    [64, 105, 0],
    [0, 255, 0],
    [64, 108, 8],
    [255, 92, 0],
    [64, 0, 0],
    [0, 255, 246],
    [255, 255, 76],
    [32, 255, 20],
    [0, 255, 1],
    [255, 92, 0],
    [64, 108, 8],
    [32, 0, 0],
    [0, 255, 0],
    [59, 255, 0],
    [49, 255, 0],
    [64, 108, 0],
    [64, 0, 0],
    [49, 0, 0],
    [64, 108, 0],
    [255, 0, 0],
    [255, 92, 0],
    [57, 224, 0],
    [0, 255, 0],
    [59, 255, 0],
    [0, 255, 0],
    [0, 255, 0],
    [64, 105, 0],
    [57, 255, 0],
    [64, 105, 8],
    [64, 105, 0],
    [0, 255, 0],
    [65, 105, 8],
    [64, 105, 8],
    [255, 66, 255],
    [74, 0, 79],
    [0, 0, 21],
    [0, 0, 1],
    [64, 105, 8],
    [64, 105, 8],
    [255, 255, 8],
    [255, 105, 0],
    [0, 255, 0],
    [255, 89, 0],
    [255, 216, 0],
    [255, 0, 0],
    [62, 0, 0],
    [0, 5, 0],
    [255, 255, 246],
    [0, 255, 76],
    [64, 105, 8],
    [255, 108, 255],
    [47, 255, 79],
    [0, 255, 0],
    [255, 255, 0],
    [64, 108, 8],
    [64, 105, 8],
    [255, 66, 255],
    [64, 116, 10],
    [57, 255, 0],
    [255, 255, 0],
    [64, 71, 0],
    [64, 105, 8],
    [64, 255, 0],
    [32, 255, 0],
    [255, 108, 0],
    [4, 255, 0],
    [255, 0, 0],
    [64, 105, 8],
    [37, 255, 0],
    [64, 105, 8],
    [64, 105, 8],
    [64, 105, 8],
    [255, 0, 0],
    [255, 92, 0],
    [0, 224, 0],
    [64, 105, 0],
    [64, 108, 8],
    [255, 255, 8],
    [255, 0, 0],
    [0, 105, 8],
    [64, 0, 0],
    [64, 105, 8],
    [64, 105, 8],
    [64, 105, 8],
    [59, 255, 8],
    [64, 132, 0],
    [65, 105, 8],
    [255, 66, 0],
    [62, 108, 8],
    [62, 105, 8],
    [255, 89, 0],
    [255, 216, 0],
    [64, 108, 8],
    [255, 0, 0],
    [0, 0, 0],
    [255, 0, 0],
    [64, 21, 0],
    [255, 89, 0],
    [255, 108, 0],
    [57, 255, 0],
    [0, 255, 0],
    [255, 89, 0],
    [64, 108, 8],
    [64, 105, 8],
    [64, 108, 0],
    [59, 255, 0],
    [57, 255, 0],
    [59, 255, 0],
    [64, 105, 0],
    [64, 71, 0],
    [0, 168, 0],
    [64, 108, 0],
    [64, 105, 8],
    [255, 105, 8],
    [64, 0, 0],
    [64, 105, 0],
    [64, 255, 8],
    [255, 255, 8],
    [32, 255, 0],
    [4, 255, 0],
    [255, 105, 0],
    [74, 255, 0],
    [84, 0, 0],
    [255, 108, 0],
    [64, 105, 8],
    [64, 105, 8],
    [255, 108, 0],
    [64, 108, 8],
    [64, 0, 0],
    [255, 21, 0],
    [0, 7, 0],
    [64, 105, 0],
    [255, 0, 0],
    [64, 105, 8],
    [255, 255, 0],
    [64, 71, 0],
    [255, 116, 10],
    [84, 255, 0],
    [64, 105, 8],
    [64, 105, 8],
    [255, 108, 8],
    [0, 255, 0],
    [255, 105, 0],
    [255, 0, 0],
    [84, 0, 0],
    [59, 0, 0],
    [64, 0, 0],
    [59, 0, 0],
    [64, 0, 0],
    [255, 0, 0],
    [64, 105, 0],
    [64, 0, 0],
    [0, 0, 0],
    [255, 21, 0],
    [64, 105, 8],
    [255, 255, 0],
    [0, 255, 0],
    [255, 92, 0],
    [64, 105, 8],
    [255, 105, 8],
    [64, 108, 8],
    [64, 134, 0],
    [64, 105, 8],
    [32, 255, 0],
    [64, 105, 8],
    [255, 255, 0],
    [59, 255, 0],
    [64, 108, 8],
    [255, 89, 0],
    [64, 21, 0],
    [255, 255, 214],
    [64, 105, 8],
    [65, 108, 8],
    [62, 255, 0],
    [0, 255, 0],
    [84, 255, 0],
    [64, 89, 0],
    [255, 7, 0],
    [255, 0, 0],
    [59, 0, 0],
    [64, 21, 0],
    [64, 105, 8],
    [64, 105, 8],
    [64, 105, 0],
    [255, 21, 0],
    [64, 105, 8],
    [255, 255, 8],
    [49, 255, 0],
    [64, 105, 8],
    [62, 105, 8],
    [62, 108, 8],
    [64, 102, 5],
    [64, 105, 8],
    [64, 108, 8],
    [74, 255, 0],
    [255, 255, 0],
    [64, 108, 8],
    [255, 105, 0],
    [64, 255, 0],
    [32, 255, 0],
    [64, 105, 8],
    [37, 255, 0],
    [255, 0, 0],
    [64, 105, 8],
    [64, 105, 8],
    [255, 255, 0],
    [0, 7, 0],
    [0, 0, 0],
    [64, 105, 8],
    [64, 108, 8],
    [64, 105, 8],
    [64, 105, 8],
    [255, 92, 0],
    [64, 21, 0],
    [255, 255, 246],
    [255, 255, 76],
    [64, 105, 8],
    [64, 105, 8],
    [255, 0, 0],
    [255, 105, 0],
    [255, 66, 8],
    [32, 0, 0],
    [255, 92, 0],
    [49, 224, 0],
    [64, 108, 8],
    [4, 255, 0],
    [59, 255, 0],
    [64, 105, 8],
    [57, 89, 0],
    [255, 255, 246],
    [4, 255, 76],
    [255, 108, 0],
    [36, 255, 0],
    [255, 0, 0],
    [255, 0, 0],
    [0, 7, 0],
    [57, 0, 0],
    [255, 116, 10],
    [255, 255, 0],
    [0, 7, 0],
    [64, 105, 0],
    [65, 105, 0],
    [255, 0, 0],
    [64, 255, 252],
    [255, 0, 78],
    [64, 105, 8],
    [74, 255, 0],
    [255, 255, 0],
    [64, 105, 8],
    [37, 255, 0],
    [62, 105, 8],
    [64, 108, 8],
    [64, 105, 8],
    [64, 105, 8],
    [255, 105, 11],
    [255, 255, 0],
    [255, 108, 165],
    [64, 105, 0],
    [32, 255, 0],
    [255, 255, 8],
    [64, 108, 8],
    [255, 105, 0],
    [4, 255, 0],
    [0, 7, 0],
    [0, 0, 0],
    [255, 255, 8],
    [255, 5, 0],
    [0, 0, 0],
    [64, 0, 0],
    [243, 0, 0],
    [255, 105, 0],
    [0, 7, 0],
    [0, 7, 0],
    [255, 108, 0],
    [84, 255, 0],
    [255, 108, 0],
    [255, 92, 0],
    [0, 224, 0],
    [255, 0, 0],
    [243, 0, 0],
    [64, 66, 0],
    [255, 92, 0],
    [0, 224, 0],
    [0, 255, 0],
    [243, 255, 0],
    [255, 108, 0],
    [217, 255, 8],
    [64, 0, 0],
    [255, 255, 8],
    [57, 255, 0],
    [59, 255, 0],
    [64, 105, 8],
    [59, 255, 0],
    [64, 0, 0],
    [0, 0, 0]
];

