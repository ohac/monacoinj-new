/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sakuracoin.params;

import com.google.sakuracoin.core.NetworkParameters;
import com.google.sakuracoin.core.Utils;
import org.spongycastle.util.encoders.Hex;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the testnet, a separate public instance of Bitcoin that has relaxed rules suitable for development
 * and testing of applications and new Bitcoin versions.
 */
public class TestNet3Params extends NetworkParameters {
    public TestNet3Params() {
        super();
        id = ID_TESTNET;
        // Genesis hash is 47ff0d1bdfb5e1716a648be600c5f8e433799928172a89f9fcbac086619a5ad9
        packetMagic = 0xfcc1b7dc;
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);
        port = 19301;
        addressHeader = 50;
        //p2shHeader = 196; //We don't have this
        acceptableAddressCodes = new int[] { addressHeader };
        dumpedPrivateKeyHeader = 239;
        genesisBlock.setTime(1394862581L);
        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setNonce(78282L);
        spendableCoinbaseDepth = 100;
        subsidyDecreaseBlockCount = 1051000;
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("47ff0d1bdfb5e1716a648be600c5f8e433799928172a89f9fcbac086619a5ad9"));
        alertSigningKey = Hex.decode("04302390343f91cc401d56d68b123028bf52e5fca1939df127f63c6467cdf9c8e2c14b61104cf817d0b780da337893ecc4aaff1309e536162dabbdb45200ca2b0a");

        dnsSeeds = new String[] {
            "skrseed.sighash.info"
        };
    }

    private static TestNet3Params instance;
    public static synchronized TestNet3Params get() {
        if (instance == null) {
            instance = new TestNet3Params();
        }
        return instance;
    }
}
