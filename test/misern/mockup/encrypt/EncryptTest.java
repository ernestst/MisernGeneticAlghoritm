package misern.mockup.encrypt;

import static org.junit.jupiter.api.Assertions.*;

class EncryptTest {

    @org.junit.jupiter.api.Test
    void toBinary() {
        assertEquals(Encrypt.toBinary(42),"101010");
        assertEquals(Encrypt.toBinary(78),"1001110");
        assertEquals(Encrypt.toBinary(112),"1110000");
        assertEquals(Encrypt.toBinary(256),"100000000");
        assertEquals(Encrypt.toBinary(1253),"10011100101");
        assertEquals(Encrypt.toBinary(5932),"1011100101100");
        assertEquals(Encrypt.toBinary(112342),"11011011011010110");
        assertEquals(Encrypt.toBinary(6434212),"11000100010110110100100");
        assertEquals(Encrypt.toBinary(543333231),"100000011000101001101101101111");
        assertEquals(Encrypt.toBinary(324534353),"10011010110000000000001010001");
    }

    @org.junit.jupiter.api.Test
    void binarytoGray() {
        assertEquals(Encrypt.toGray(42),"111111");
        assertEquals(Encrypt.toGray(78),"1101001");
        assertEquals(Encrypt.toGray(112),"1001000");
        assertEquals(Encrypt.toGray(256),"110000000");
        assertEquals(Encrypt.toGray(1253),"11010010111");
        assertEquals(Encrypt.toGray(5932),"1110010111010");
        assertEquals(Encrypt.toGray(112342),"10110110110111101");
        assertEquals(Encrypt.toGray(6434212),"10100110011101101110110");
        assertEquals(Encrypt.toGray(543333231),"110000010100111101011011011000");
        assertEquals(Encrypt.toGray(324534353),"11010111101000000000001111001");
    }

    @org.junit.jupiter.api.Test
    void graytoBinary() {
        assertEquals(Encrypt.graytoBinary("11"),"10");
        assertEquals(Encrypt.graytoBinary("1101001"),"1001110");
        assertEquals(Encrypt.graytoBinary("1001000"),"1110000");
        assertEquals(Encrypt.graytoBinary("110000000"),"100000000");
        assertEquals(Encrypt.graytoBinary("11010010111"),"10011100101");
        assertEquals(Encrypt.graytoBinary("1110010111010"),"1011100101100");
        assertEquals(Encrypt.graytoBinary("10110110110111101"),"11011011011010110");
        assertEquals(Encrypt.graytoBinary("10100110011101101110110"),"11000100010110110100100");
        assertEquals(Encrypt.graytoBinary("110000010100111101011011011000"),"100000011000101001101101101111");
        assertEquals(Encrypt.graytoBinary("11010111101000000000001111001"),"10011010110000000000001010001");
    }
}