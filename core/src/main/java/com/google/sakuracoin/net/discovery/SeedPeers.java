/**
 * Copyright 2011 Micheal Swiggs
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
package com.google.sakuracoin.net.discovery;

import com.google.sakuracoin.core.NetworkParameters;

import javax.annotation.Nullable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * SeedPeers stores a pre-determined list of Bitcoin node addresses. These nodes are selected based on being
 * active on the network for a long period of time. The intention is to be a last resort way of finding a connection
 * to the network, in case IRC and DNS fail. The list comes from the Bitcoin C++ source code.
 */
public class SeedPeers implements PeerDiscovery {
    private NetworkParameters params;
    private int pnseedIndex;

    public SeedPeers(NetworkParameters params) {
        this.params = params;
    }

    /**
     * Acts as an iterator, returning the address of each node in the list sequentially.
     * Once all the list has been iterated, null will be returned for each subsequent query.
     *
     * @return InetSocketAddress - The address/port of the next node.
     * @throws PeerDiscoveryException
     */
    @Nullable
    public InetSocketAddress getPeer() throws PeerDiscoveryException {
        try {
            return nextPeer();
        } catch (UnknownHostException e) {
            throw new PeerDiscoveryException(e);
        }
    }

    @Nullable
    private InetSocketAddress nextPeer() throws UnknownHostException {
        if (pnseedIndex >= seedAddrs.length) return null;
        return new InetSocketAddress(convertAddress(seedAddrs[pnseedIndex++]),
                params.getPort());
    }

    /**
     * Returns an array containing all the Bitcoin nodes within the list.
     */
    public InetSocketAddress[] getPeers(long timeoutValue, TimeUnit timeoutUnit) throws PeerDiscoveryException {
        try {
            return allPeers();
        } catch (UnknownHostException e) {
            throw new PeerDiscoveryException(e);
        }
    }

    private InetSocketAddress[] allPeers() throws UnknownHostException {
        InetSocketAddress[] addresses = new InetSocketAddress[seedAddrs.length];
        for (int i = 0; i < seedAddrs.length; ++i) {
            addresses[i] = new InetSocketAddress(convertAddress(seedAddrs[i]), params.getPort());
        }
        return addresses;
    }

    private InetAddress convertAddress(int seed) throws UnknownHostException {
        byte[] v4addr = new byte[4];
        v4addr[0] = (byte) (0xFF & (seed));
        v4addr[1] = (byte) (0xFF & (seed >> 8));
        v4addr[2] = (byte) (0xFF & (seed >> 16));
        v4addr[3] = (byte) (0xFF & (seed >> 24));
        return InetAddress.getByAddress(v4addr);
    }

    public static int[] seedAddrs =
            {
    0x029b377d,
    0x0ec66d76,
    0x0f5ea8b6,
    0x12397999,
    0x1a3f9870,
    0x1b44933b,
    0x216d68db,
    0x2200b073,
    0x290af285,
    0x30766cdc,
    0x3314d4ca,
    0x346ed459,
    0x35670a76,
    0x41a8bc6a,
    0x42451b7e,
    0x434ea3b6,
    0x48037899,
    0x50f1fdd2,
    0x52e45074,
    0x54e5d86f,
    0x56d78c65,
    0x5cd45274,
    0x6296543b,
    0x636d07de,
    0x64b7d27a,
    0x66e6d86f,
    0x72da5e74,
    0x7864800e,
    0x8261d33d,
    0x84b80076,
    0x8546061f,
    0x8878dbda,
    0x8e915f3a,
    0x8f5bed3c,
    0x911bbcd2,
    0x9278dbda,
    0x98a2c03d,
    0x99840224,
    0x9b416e45,
    0x9c25847a,
    0x9def91dc,
    0x9ee9c27d,
    0xa644dfdf,
    0xa7d35e74,
    0xa8adbf3a,
    0xb05fd27c,
    0xb201b073,
    0xb7873378,
    0xb865ca85,
    0xb99930b4,
    0xb9f1b1bf,
    0xbe3dfbda,
    0xbe4cad6a,
    0xbe651cb4,
    0xbffa243c,
    0xc1536adb,
    0xc399686f,
    0xc7241b7c,
    0xd272800e,
    0xd3a13f77,
    0xd7cf79dd,
    0xd91ad7ca,
    0xdc0fc33d,
    0xddf85edb,
    0xde8ed9da,
    0xe33f4150,
    0xe4ebe2ca,
    0xe5b46f65,
    0xe7e8016e,
    0xe8419771,
    0xf53bd185,
    0xf72cb572,
    0xfb449271,
    0xfeb3d67c,
            };
    
    public void shutdown() {
    }
}
